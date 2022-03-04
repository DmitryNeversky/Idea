import {Component, OnInit} from '@angular/core';
import {SharedService} from "../../../shared/shared.service";

@Component({
  selector: 'app-interface',
  templateUrl: './interface.component.html',
  styleUrls: ['./interface.component.css']
})
export class InterfaceComponent implements OnInit {

  darkMode: boolean;

  constructor(private sharedService: SharedService) { }

  ngOnInit(): void {
    this.darkMode = !!localStorage.getItem('dark-mode');
  }

  toggle() {
    this.darkMode = !this.darkMode;
    if (this.darkMode) {
      localStorage.setItem('dark-mode', 'true');
    } else {
      localStorage.removeItem('dark-mode');
    }
    this.sharedService.emitDarkMode();
  }

  changeLightMode() {
    this.darkMode = false;
    localStorage.removeItem('dark-mode')
    this.sharedService.emitDarkMode();
  }

  changeDarkMode() {
    this.darkMode = true;
    localStorage.setItem('dark-mode', 'true')
    this.sharedService.emitDarkMode();
  }
}
