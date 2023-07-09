import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {MatChipInputEvent} from "@angular/material/chips";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {Tag} from "../../models/Tag";
import {MatLegacyChipInputEvent} from "@angular/material/legacy-chips";

@Component({
  selector: 'app-chips',
  templateUrl: './chips.component.html',
  styleUrls: ['./chips.component.css']
})
export class ChipsComponent {

  @Output()
  tagsEmitter: EventEmitter<Tag[]> = new EventEmitter<Tag[]>();

  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  tagCtrl = new FormControl();
  filteredTags: Observable<Tag[]>;

  @Input()
  allTags: Tag[];

  @Input()
  tags: Tag[] = [];

  @ViewChild('tagInput') tagInput: ElementRef<HTMLInputElement>;

  constructor() {
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
        startWith(null),
        map((tag: Tag | null) => tag ? this._filter(tag) : this.allTags.slice()));
  }

  add(event: MatLegacyChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value && !this.tags.find(t => t.name == value)) {
      this.tags.push(this.tags.find(t => t.name == value));
    }

    event.chipInput!.clear();

    this.tagCtrl.setValue(null);
  }

  remove(tag: Tag): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if(!this.tags.includes(event.option.value))
      this.tags.push(event.option.value);
    this.tagInput.nativeElement.value = '';
    this.tagCtrl.setValue(null);
  }

  private _filter(value: Tag): Tag[] {

    return this.allTags.filter(tag => tag == value);
  }

  emitChips() {
    this.tagsEmitter.emit(this.allTags);
  }
}
