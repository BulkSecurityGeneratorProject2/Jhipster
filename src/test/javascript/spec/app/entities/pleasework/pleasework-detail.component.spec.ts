import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PleaseworkDetailComponent } from '../../../../../../main/webapp/app/entities/pleasework/pleasework-detail.component';
import { PleaseworkService } from '../../../../../../main/webapp/app/entities/pleasework/pleasework.service';
import { Pleasework } from '../../../../../../main/webapp/app/entities/pleasework/pleasework.model';

describe('Component Tests', () => {

    describe('Pleasework Management Detail Component', () => {
        let comp: PleaseworkDetailComponent;
        let fixture: ComponentFixture<PleaseworkDetailComponent>;
        let service: PleaseworkService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PleaseworkDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    PleaseworkService
                ]
            }).overrideComponent(PleaseworkDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PleaseworkDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PleaseworkService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Pleasework(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pleasework).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
