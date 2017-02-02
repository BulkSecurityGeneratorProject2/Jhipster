import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Pleasework } from './pleasework.model';
import { PleaseworkService } from './pleasework.service';

@Component({
    selector: 'jhi-pleasework-detail',
    templateUrl: './pleasework-detail.component.html'
})
export class PleaseworkDetailComponent implements OnInit, OnDestroy {

    pleasework: Pleasework;
    private subscription: any;

    constructor(
        private pleaseworkService: PleaseworkService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.pleaseworkService.find(id).subscribe(pleasework => {
            this.pleasework = pleasework;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
