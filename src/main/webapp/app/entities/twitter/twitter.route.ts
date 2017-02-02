import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TwitterComponent } from './twitter.component';
import { TwitterDetailComponent } from './twitter-detail.component';
import { TwitterPopupComponent } from './twitter-dialog.component';
import { TwitterDeletePopupComponent } from './twitter-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TwitterResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const twitterRoute: Routes = [
  {
    path: 'twitter',
    component: TwitterComponent,
    resolve: {
      'pagingParams': TwitterResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Twitters'
    }
  }, {
    path: 'twitter/:id',
    component: TwitterDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Twitters'
    }
  }
];

export const twitterPopupRoute: Routes = [
  {
    path: 'twitter-new',
    component: TwitterPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Twitters'
    },
    outlet: 'popup'
  },
  {
    path: 'twitter/:id/edit',
    component: TwitterPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Twitters'
    },
    outlet: 'popup'
  },
  {
    path: 'twitter/:id/delete',
    component: TwitterDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Twitters'
    },
    outlet: 'popup'
  }
];
