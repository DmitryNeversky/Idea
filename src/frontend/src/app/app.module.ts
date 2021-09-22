import {NgModule, Provider} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatDividerModule} from "@angular/material/divider";
import {MatListModule} from "@angular/material/list";
import {MatBadgeModule} from "@angular/material/badge";
import {MatCardModule} from "@angular/material/card";
import {MatGridListModule} from "@angular/material/grid-list";
import {IdeaService} from "./services/idea.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {IdeasComponent} from './templates/ideas/ideas.component';
import {MatMenuModule} from "@angular/material/menu";
import {AppRoutingModule} from "./app-routing.module";
import {CreateIdeaComponent} from './templates/create-idea/create-idea.component';
import {MatInputModule} from "@angular/material/input";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {IdeaFillComponent} from "./templates/idea-fill/idea-fill.component";
import {HomeComponent} from './templates/home/home.component';
import {MatDialogModule} from "@angular/material/dialog";
import {SharedModule} from "./shared/shared.module";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {TruncatePipe} from './pipes/truncate.pipe';
import {MatTableModule} from "@angular/material/table";
import {FlexLayoutModule} from "@angular/flex-layout";
import {SharedService} from "./shared/shared.service";
import {UpdateIdeaComponent} from './templates/update-idea/update-idea.component';
import {ImagevarPipe} from './pipes/imagevar.pipe';
import {FilenamePipe} from './pipes/filename.pipe';
import {SizePipe} from './pipes/size.pipe';
import {MatTooltipModule} from "@angular/material/tooltip";
import {IdeaComponent} from "./templates/idea/idea.component";
import {RegistrationComponent} from './templates/registration/registration.component';
import {LoginComponent} from './templates/login/login.component';
import {MatStepperModule} from "@angular/material/stepper";
import {MatRadioModule} from "@angular/material/radio";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, MatNativeDateModule} from "@angular/material/core";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {UserComponent} from './templates/user/user.component';
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {UserIdeasComponent} from './templates/user/ideas/user-ideas.component';
import {NgxMaskModule} from "ngx-mask";
import {SettingsComponent} from './templates/settings/settings.component';
import {MatTabsModule} from "@angular/material/tabs";
import {ProfileComponent} from './templates/settings/profile/profile.component';
import {NotifiesComponent} from './templates/settings/notifies/notifies.component';
import {InterfaceComponent} from './templates/settings/interface/interface.component';
import {SecureComponent} from './templates/settings/secure/secure.component';
import {UserProfileComponent} from "./templates/user/profile/user-profile.component";
import {UserContactsComponent} from './templates/user/contacts/user-contacts.component';
import {AdminComponent} from './templates/admin/admin.component';
import {AdminUsersComponent} from './templates/admin/admin-users/admin-users.component';
import {UserItemComponent} from './templates/admin/admin-users/user-item/user-item.component';
import {AdminTagsComponent} from './templates/admin/admin-tags/admin-tags.component';
import {MatChipsModule} from "@angular/material/chips";
import {AdminPostsComponent} from './templates/admin/admin-posts/admin-posts.component';
import {MatSortModule} from "@angular/material/sort";
import {ModalComponent} from './templates/admin/modal/modal.component';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatSliderModule} from "@angular/material/slider";

const INTERCEPTOR_PROVIDERS: Provider[] = [
    {
        provide: HTTP_INTERCEPTORS,
        useClass: AuthInterceptor,
        multi: true
    },
]

@NgModule({
  declarations: [
    AppComponent,
    IdeasComponent,
    IdeaComponent,
    CreateIdeaComponent,
    IdeaFillComponent,
    HomeComponent,
    TruncatePipe,
    UpdateIdeaComponent,
    ImagevarPipe,
    FilenamePipe,
    SizePipe,
    RegistrationComponent,
    LoginComponent,
    UserComponent,
    UserIdeasComponent,
    SettingsComponent,
    ProfileComponent,
    NotifiesComponent,
    InterfaceComponent,
    SecureComponent,
    UserProfileComponent,
    UserContactsComponent,
    AdminComponent,
    AdminUsersComponent,
    UserItemComponent,
    AdminTagsComponent,
    AdminPostsComponent,
    ModalComponent,
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,

        MatSidenavModule,
        MatFormFieldModule,
        MatSelectModule,
        MatButtonModule,
        MatIconModule,
        MatToolbarModule,
        MatDividerModule,
        MatListModule,
        MatBadgeModule,
        MatCardModule,
        MatGridListModule,
        MatMenuModule,
        MatInputModule,
        MatPaginatorModule,
        MatButtonToggleModule,
        MatDialogModule,

        HttpClientModule,
        ReactiveFormsModule,
        RouterModule,
        AppRoutingModule,
        FormsModule,
        SharedModule,
        MatProgressBarModule,
        MatTableModule,
        FlexLayoutModule,
        MatTooltipModule,
        MatStepperModule,
        MatRadioModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatProgressSpinnerModule,
        NgxMaskModule.forRoot(),
        MatTabsModule,
        MatChipsModule,
        MatSortModule,
        MatSlideToggleModule,
        MatSliderModule,
    ],
  providers: [IdeaService, SharedService, INTERCEPTOR_PROVIDERS,
          {
            provide: STEPPER_GLOBAL_OPTIONS,
            useValue: { displayDefaultIndicatorType: false }
          },
          {
              provide: MAT_DATE_LOCALE,
              useValue: 'en-GB',
          }
      ],
  bootstrap: [AppComponent]
})
export class AppModule { }
