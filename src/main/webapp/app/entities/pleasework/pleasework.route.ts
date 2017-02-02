import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PleaseworkComponent } from './pleasework.component';
import { PleaseworkDetailComponent } from './pleasework-detail.component';
import { PleaseworkPopupComponent } from './pleasework-dialog.component';
import { PleaseworkDeletePopupComponent } from './pleasework-delete-dialog.component';

import { Principal } from '../../shared';


export const pleaseworkRoute: Routes = [
  {
    path: 'pleasework',
    component: PleaseworkComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleaseworks'
    }
  }, {
    path: 'pleasework/:id',
    component: PleaseworkDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleaseworks'
    }
  }
];

export const pleaseworkPopupRoute: Routes = [
  {
    path: 'pleasework-new',
    component: PleaseworkPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleaseworks'
    },
    outlet: 'popup'
  },
  {
    path: 'pleasework/:id/edit',
    component: PleaseworkPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleaseworks'
    },
    outlet: 'popup'
  },
  {
    path: 'pleasework/:id/delete',
    component: PleaseworkDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pleaseworks'
    },
    outlet: 'popup'
  }
];
