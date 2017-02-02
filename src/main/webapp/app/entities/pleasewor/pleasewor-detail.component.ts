import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Pleasewor } from './pleasewor.model';
import { PleaseworService } from './pleasewor.service';

@Component({
    selector: 'jhi-pleasewor-detail',
    templateUrl: './pleasewor-detail.component.html'
})
export class PleaseworDetailComponent implements OnInit, OnDestroy {

    pleasewor: Pleasewor;
    private subscription: any;

    constructor(
        private pleaseworService: PleaseworService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.pleaseworService.find(id).subscribe(pleasewor => {
            this.pleasewor = pleasewor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
