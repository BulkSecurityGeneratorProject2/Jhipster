import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Pleasework } from './pleasework.model';
import { PleaseworkService } from './pleasework.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pleasework',
    templateUrl: './pleasework.component.html'
})
export class PleaseworkComponent implements OnInit, OnDestroy {
pleaseworks: Pleasework[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pleaseworkService: PleaseworkService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pleaseworkService.query().subscribe(
            (res: Response) => {
                this.pleaseworks = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPleaseworks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: Pleasework) {
        return item.id;
    }



    registerChangeInPleaseworks() {
        this.eventSubscriber = this.eventManager.subscribe('pleaseworkListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
