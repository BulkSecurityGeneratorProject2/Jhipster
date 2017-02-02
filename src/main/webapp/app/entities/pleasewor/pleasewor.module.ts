import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinalSharedModule } from '../../shared';

import {
    PleaseworService,
    PleaseworPopupService,
    PleaseworComponent,
    PleaseworDetailComponent,
    PleaseworDialogComponent,
    PleaseworPopupComponent,
    PleaseworDeletePopupComponent,
    PleaseworDeleteDialogComponent,
    pleaseworRoute,
    pleaseworPopupRoute,
    PleaseworResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...pleaseworRoute,
    ...pleaseworPopupRoute,
];

@NgModule({
    imports: [
        FinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PleaseworComponent,
        PleaseworDetailComponent,
        PleaseworDialogComponent,
        PleaseworDeleteDialogComponent,
        PleaseworPopupComponent,
        PleaseworDeletePopupComponent,
    ],
    entryComponents: [
        PleaseworComponent,
        PleaseworDialogComponent,
        PleaseworPopupComponent,
        PleaseworDeleteDialogComponent,
        PleaseworDeletePopupComponent,
    ],
    providers: [
        PleaseworService,
        PleaseworPopupService,
        PleaseworResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinalPleaseworModule {}
