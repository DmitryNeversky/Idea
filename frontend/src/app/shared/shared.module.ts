import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DialogComponent} from "./dialog/dialog.component";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import { SnackbarComponent } from './snackbar/snackbar.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { ChipsComponent } from './chips/chips.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatChipsModule} from "@angular/material/chips";
import {MatIconModule} from "@angular/material/icon";
import {ReactiveFormsModule} from "@angular/forms";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatOptionModule} from "@angular/material/core";
import {MatLegacyChipsModule} from "@angular/material/legacy-chips";

@NgModule({
    declarations: [
        DialogComponent,
        SnackbarComponent,
        ChipsComponent,
    ],
    exports: [
        ChipsComponent,
        MatLegacyChipsModule,
    ],
    imports: [
        CommonModule,
        MatDialogModule,
        MatButtonModule,
        MatSnackBarModule,
        MatFormFieldModule,
        MatLegacyChipsModule,
        MatIconModule,
        ReactiveFormsModule,
        MatAutocompleteModule,
        MatOptionModule
    ]
})
export class SharedModule { }
