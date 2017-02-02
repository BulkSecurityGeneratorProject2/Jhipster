import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Twitter } from './twitter.model';
import { TwitterPopupService } from './twitter-popup.service';
import { TwitterService } from './twitter.service';
@Component({
    selector: 'jhi-twitter-dialog',
    templateUrl: './twitter-dialog.component.html'
})
export class TwitterDialogComponent implements OnInit {

    twitter: Twitter;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private twitterService: TwitterService,
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
        if (this.twitter.id !== undefined) {
            this.twitterService.update(this.twitter)
                .subscribe((res: Response) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.twitterService.create(this.twitter)
                .subscribe((res: Response) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result) {
        this.eventManager.broadcast({ name: 'twitterListModification', content: 'OK'});
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
    selector: 'jhi-twitter-popup',
    template: ''
})
export class TwitterPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private twitterPopupService: TwitterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.twitterPopupService
                    .open(TwitterDialogComponent, params['id']);
            } else {
                this.modalRef = this.twitterPopupService
                    .open(TwitterDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
