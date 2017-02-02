import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Twitter } from './twitter.model';
import { TwitterService } from './twitter.service';
@Injectable()
export class TwitterPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private twitterService: TwitterService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.twitterService.find(id).subscribe(twitter => {
                this.twitterModalRef(component, twitter);
            });
        } else {
            return this.twitterModalRef(component, new Twitter());
        }
    }

    twitterModalRef(component: Component, twitter: Twitter): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.twitter = twitter;
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
