import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'size'
})
export class SizePipe implements PipeTransform {

  transform(map: Map<string, string>): number {
    let counter = 0;

    for (let mapKey in map)
      counter++;

    return counter;
  }

}
