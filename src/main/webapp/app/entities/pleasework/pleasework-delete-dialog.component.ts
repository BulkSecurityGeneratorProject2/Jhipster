import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Pleasework } from './pleasework.model';
import { PleaseworkPopupService } from './pleasework-popup.service';
import { PleaseworkService } from './pleasework.service';

@Component({
    selector: 'jhi-pleasework-delete-dialog',
    templateUrl: './pleasework-delete-dialog.component.html'
})
export class PleaseworkDeleteDialogComponent {

    pleasework: Pleasework;

    constructor(
        private pleaseworkService: PleaseworkService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.pleaseworkService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pleaseworkListModification',
                content: 'Deleted an pleasework'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pleasework-delete-popup',
    template: ''
})
export class PleaseworkDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private pleaseworkPopupService: PleaseworkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.pleaseworkPopupService
                .open(PleaseworkDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
