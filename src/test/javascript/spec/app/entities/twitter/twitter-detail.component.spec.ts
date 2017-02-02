import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TwitterDetailComponent } from '../../../../../../main/webapp/app/entities/twitter/twitter-detail.component';
import { TwitterService } from '../../../../../../main/webapp/app/entities/twitter/twitter.service';
import { Twitter } from '../../../../../../main/webapp/app/entities/twitter/twitter.model';

describe('Component Tests', () => {

    describe('Twitter Management Detail Component', () => {
        let comp: TwitterDetailComponent;
        let fixture: ComponentFixture<TwitterDetailComponent>;
        let service: TwitterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TwitterDetailComponent],
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
                    TwitterService
                ]
            }).overrideComponent(TwitterDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TwitterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TwitterService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Twitter(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.twitter).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
