import {NgModule} from '@angular/core';
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
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {IdeasComponent} from './ideas/ideas.component';
import {MatMenuModule} from "@angular/material/menu";
import {AppRoutingModule} from "./app-routing.module";
import {CreateIdeaComponent} from './create-idea/create-idea.component';
import {MatInputModule} from "@angular/material/input";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {IdeaFillComponent} from "./idea-fill/idea-fill.component";
import { HomeComponent } from './home/home.component';
import {MatDialogModule} from "@angular/material/dialog";
import {SharedModule} from "./shared/shared.module";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import { TruncatePipe } from './pipes/truncate.pipe';
import {MatTableModule} from "@angular/material/table";
import {FlexLayoutModule} from "@angular/flex-layout";
import {SharedService} from "./shared/shared.service";
import { UpdateIdeaComponent } from './update-idea/update-idea.component';
import { ImagevarPipe } from './pipes/imagevar.pipe';
import { FilenamePipe } from './pipes/filename.pipe';
import { SizePipe } from './pipes/size.pipe';
import {MatTooltipModule} from "@angular/material/tooltip";
import {IdeaComponent} from "./idea/idea.component";

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
    ],
  providers: [IdeaService, SharedService],
  bootstrap: [AppComponent]
})
export class AppModule { }
