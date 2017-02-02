import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Twitter } from './twitter.model';
import { TwitterPopupService } from './twitter-popup.service';
import { TwitterService } from './twitter.service';

@Component({
    selector: 'jhi-twitter-delete-dialog',
    templateUrl: './twitter-delete-dialog.component.html'
})
export class TwitterDeleteDialogComponent {

    twitter: Twitter;

    constructor(
        private twitterService: TwitterService,
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
        this.twitterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'twitterListModification',
                content: 'Deleted an twitter'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-twitter-delete-popup',
    template: ''
})
export class TwitterDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private twitterPopupService: TwitterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.twitterPopupService
                .open(TwitterDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
