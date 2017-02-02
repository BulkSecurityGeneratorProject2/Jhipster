import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pleasewor } from './pleasewor.model';
import { PleaseworService } from './pleasewor.service';
@Injectable()
export class PleaseworPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private pleaseworService: PleaseworService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pleaseworService.find(id).subscribe(pleasewor => {
                this.pleaseworModalRef(component, pleasewor);
            });
        } else {
            return this.pleaseworModalRef(component, new Pleasewor());
        }
    }

    pleaseworModalRef(component: Component, pleasewor: Pleasewor): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pleasewor = pleasewor;
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
