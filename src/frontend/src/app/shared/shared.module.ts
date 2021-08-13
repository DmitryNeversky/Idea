import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DialogComponent} from "./dialog/dialog.component";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import { SnackbarComponent } from './snackbar/snackbar.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";

@NgModule({
  declarations: [
      DialogComponent,
      SnackbarComponent
  ],
    imports: [
        CommonModule,
        MatDialogModule,
        MatButtonModule,
        MatSnackBarModule
    ]
})
export class SharedModule { }
