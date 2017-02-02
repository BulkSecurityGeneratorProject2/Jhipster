import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Pleasewor } from './pleasewor.model';
import { PleaseworPopupService } from './pleasewor-popup.service';
import { PleaseworService } from './pleasewor.service';

@Component({
    selector: 'jhi-pleasewor-delete-dialog',
    templateUrl: './pleasewor-delete-dialog.component.html'
})
export class PleaseworDeleteDialogComponent {

    pleasewor: Pleasewor;

    constructor(
        private pleaseworService: PleaseworService,
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
        this.pleaseworService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pleaseworListModification',
                content: 'Deleted an pleasewor'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pleasewor-delete-popup',
    template: ''
})
export class PleaseworDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private pleaseworPopupService: PleaseworPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.pleaseworPopupService
                .open(PleaseworDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
