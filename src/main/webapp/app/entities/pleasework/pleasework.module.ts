import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinalSharedModule } from '../../shared';

import {
    PleaseworkService,
    PleaseworkPopupService,
    PleaseworkComponent,
    PleaseworkDetailComponent,
    PleaseworkDialogComponent,
    PleaseworkPopupComponent,
    PleaseworkDeletePopupComponent,
    PleaseworkDeleteDialogComponent,
    pleaseworkRoute,
    pleaseworkPopupRoute,
} from './';

let ENTITY_STATES = [
    ...pleaseworkRoute,
    ...pleaseworkPopupRoute,
];

@NgModule({
    imports: [
        FinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PleaseworkComponent,
        PleaseworkDetailComponent,
        PleaseworkDialogComponent,
        PleaseworkDeleteDialogComponent,
        PleaseworkPopupComponent,
        PleaseworkDeletePopupComponent,
    ],
    entryComponents: [
        PleaseworkComponent,
        PleaseworkDialogComponent,
        PleaseworkPopupComponent,
        PleaseworkDeleteDialogComponent,
        PleaseworkDeletePopupComponent,
    ],
    providers: [
        PleaseworkService,
        PleaseworkPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinalPleaseworkModule {}
