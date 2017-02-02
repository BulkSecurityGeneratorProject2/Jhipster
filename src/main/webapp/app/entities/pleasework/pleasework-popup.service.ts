import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pleasework } from './pleasework.model';
import { PleaseworkService } from './pleasework.service';
@Injectable()
export class PleaseworkPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private pleaseworkService: PleaseworkService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pleaseworkService.find(id).subscribe(pleasework => {
                this.pleaseworkModalRef(component, pleasework);
            });
        } else {
            return this.pleaseworkModalRef(component, new Pleasework());
        }
    }

    pleaseworkModalRef(component: Component, pleasework: Pleasework): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pleasework = pleasework;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.isOpen = false;
        });
        return modalRef;
    }
}
