import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Twitter } from './twitter.model';
import { TwitterService } from './twitter.service';

@Component({
    selector: 'jhi-twitter-detail',
    templateUrl: './twitter-detail.component.html'
})
export class TwitterDetailComponent implements OnInit, OnDestroy {

    twitter: Twitter;
    private subscription: any;

    constructor(
        private twitterService: TwitterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.twitterService.find(id).subscribe(twitter => {
            this.twitter = twitter;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
