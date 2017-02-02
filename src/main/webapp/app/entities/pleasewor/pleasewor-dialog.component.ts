import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Pleasewor } from './pleasewor.model';
import { PleaseworPopupService } from './pleasewor-popup.service';
import { PleaseworService } from './pleasewor.service';
@Component({
    selector: 'jhi-pleasewor-dialog',
    templateUrl: './pleasewor-dialog.component.html'
})
export class PleaseworDialogComponent implements OnInit {

    pleasewor: Pleasewor;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pleaseworService: PleaseworService,
        private eventManager: EventManager,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    save () {
        this.isSaving = true;
        if (this.pleasewor.id !== undefined) {
            this.pleaseworService.update(this.pleasewor)
                .subscribe((res: Response) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.pleaseworService.create(this.pleasewor)
                .subscribe((res: Response) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'pleaseworListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-pleasewor-popup',
    template: ''
})
export class PleaseworPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private pleaseworPopupService: PleaseworPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.pleaseworPopupService
                    .open(PleaseworDialogComponent, params['id']);
            } else {
                this.modalRef = this.pleaseworPopupService
                    .open(PleaseworDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
