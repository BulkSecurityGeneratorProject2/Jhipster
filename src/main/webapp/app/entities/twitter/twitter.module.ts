import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinalSharedModule } from '../../shared';

import {
    TwitterService,
    TwitterPopupService,
    TwitterComponent,
    TwitterDetailComponent,
    TwitterDialogComponent,
    TwitterPopupComponent,
    TwitterDeletePopupComponent,
    TwitterDeleteDialogComponent,
    twitterRoute,
    twitterPopupRoute,
    TwitterResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...twitterRoute,
    ...twitterPopupRoute,
];

@NgModule({
    imports: [
        FinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TwitterComponent,
        TwitterDetailComponent,
        TwitterDialogComponent,
        TwitterDeleteDialogComponent,
        TwitterPopupComponent,
        TwitterDeletePopupComponent,
    ],
    entryComponents: [
        TwitterComponent,
        TwitterDialogComponent,
        TwitterPopupComponent,
        TwitterDeleteDialogComponent,
        TwitterDeletePopupComponent,
    ],
    providers: [
        TwitterService,
        TwitterPopupService,
        TwitterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinalTwitterModule {}
