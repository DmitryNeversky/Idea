import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
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
import { IdeaItemComponent } from './idea-item/idea-item.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {IdeaService} from "./services/idea.service";
import {HttpClientModule} from "@angular/common/http";
import { CreateIdeaPageComponent } from './create-idea-page/create-idea-page.component';
import {ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import { IdeasComponent } from './ideas/ideas.component';
import { IdeaComponent } from './idea/idea.component';

@NgModule({
  declarations: [
    AppComponent,
    IdeaItemComponent,
    CreateIdeaPageComponent,
    IdeasComponent,
    IdeaComponent,
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
        HttpClientModule,
        ReactiveFormsModule,
        RouterModule
    ],
  providers: [IdeaService],
  bootstrap: [AppComponent]
})
export class AppModule { }
