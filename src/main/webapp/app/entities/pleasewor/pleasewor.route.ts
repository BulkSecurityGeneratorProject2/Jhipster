import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PleaseworComponent } from './pleasewor.component';
import { PleaseworDetailComponent } from './pleasewor-detail.component';
import { PleaseworPopupComponent } from './pleasewor-dialog.component';
import { PleaseworDeletePopupComponent } from './pleasewor-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PleaseworResolvePagingParams implements Resolve<any> {

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

export const pleaseworRoute: Routes = [
  {
    path: 'pleasewor',
    component: PleaseworComponent,
    resolve: {
      'pagingParams': PleaseworResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleasewors'
    }
  }, {
    path: 'pleasewor/:id',
    component: PleaseworDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleasewors'
    }
  }
];

export const pleaseworPopupRoute: Routes = [
  {
    path: 'pleasewor-new',
    component: PleaseworPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleasewors'
    },
    outlet: 'popup'
  },
  {
    path: 'pleasewor/:id/edit',
    component: PleaseworPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleasewors'
    },
    outlet: 'popup'
  },
  {
    path: 'pleasewor/:id/delete',
    component: PleaseworDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleasewors'
    },
    outlet: 'popup'
  }
];
