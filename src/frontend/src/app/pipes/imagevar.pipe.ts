import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'imagevar'
})
export class ImagevarPipe implements PipeTransform {

  transform(value: string): string {

    value = value.split('.').pop();

    switch (value) {
      case 'xlsx':
      case 'xls':
        return "excel.png"
      case 'doc':
      case 'docx':
        return "word.png"
      case 'ppt':
      case 'pptx':
        return "point.png"
      case 'pdf':
        return "pdf.png"
      case 'xml':
      case 'txt':
        return "file.png"
    }

    return value;
  }

}
