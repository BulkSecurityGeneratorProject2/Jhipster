import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Pleasework } from './pleasework.model';
import { PleaseworkPopupService } from './pleasework-popup.service';
import { PleaseworkService } from './pleasework.service';
@Component({
    selector: 'jhi-pleasework-dialog',
    templateUrl: './pleasework-dialog.component.html'
})
export class PleaseworkDialogComponent implements OnInit {

    pleasework: Pleasework;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pleaseworkService: PleaseworkService,
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
        if (this.pleasework.id !== undefined) {
            this.pleaseworkService.update(this.pleasework)
                .subscribe((res: Response) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.pleaseworkService.create(this.pleasework)
                .subscribe((res: Response) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'pleaseworkListModification', content: 'OK'});
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
    selector: 'jhi-pleasework-popup',
    template: ''
})
export class PleaseworkPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private pleaseworkPopupService: PleaseworkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.pleaseworkPopupService
                    .open(PleaseworkDialogComponent, params['id']);
            } else {
                this.modalRef = this.pleaseworkPopupService
                    .open(PleaseworkDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
