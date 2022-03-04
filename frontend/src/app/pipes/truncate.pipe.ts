import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {

  transform(text: string, max: number, resize: number = 4): string {

    if(window.screen.width <= 600 && text.length > Math.round(max / resize))
      return text.slice(0, Math.round(max / resize)) + "...";

    else if(window.screen.width <= 600 && text.length < Math.round(max / resize))
      return text;

    else if(window.screen.width > 600 && text.length > max)
      return text.slice(0, max) + "..."

    else if(window.screen.width > 600 && text.length < max)
      return text;

    else return text;
  }

}
