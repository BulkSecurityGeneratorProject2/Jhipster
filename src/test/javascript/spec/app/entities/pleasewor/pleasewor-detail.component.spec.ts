import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PleaseworDetailComponent } from '../../../../../../main/webapp/app/entities/pleasewor/pleasewor-detail.component';
import { PleaseworService } from '../../../../../../main/webapp/app/entities/pleasewor/pleasewor.service';
import { Pleasewor } from '../../../../../../main/webapp/app/entities/pleasewor/pleasewor.model';

describe('Component Tests', () => {

    describe('Pleasewor Management Detail Component', () => {
        let comp: PleaseworDetailComponent;
        let fixture: ComponentFixture<PleaseworDetailComponent>;
        let service: PleaseworService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PleaseworDetailComponent],
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
                    PleaseworService
                ]
            }).overrideComponent(PleaseworDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PleaseworDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PleaseworService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Pleasewor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pleasewor).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
