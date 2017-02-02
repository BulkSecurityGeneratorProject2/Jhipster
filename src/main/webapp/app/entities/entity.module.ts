import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FinalPleaseworkModule } from './pleasework/pleasework.module';
import { FinalPleaseworModule } from './pleasewor/pleasewor.module';
import { FinalTwitterModule } from './twitter/twitter.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FinalPleaseworkModule,
        FinalPleaseworModule,
        FinalTwitterModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinalEntityModule {}
