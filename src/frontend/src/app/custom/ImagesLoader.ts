export class ImagesLoader {

    loadImages: any = [];
    dataTransfer: DataTransfer = new DataTransfer();

    constructor() {}

    load(event: any) {

        let component = this;

        if (event.target.files && event.target.files[0]) {

            for (const file of event.target.files) {

                let ext = file.name.match(/\.([^\.]+)$/)[1];

                switch (ext) {
                    case 'jpg':
                    case 'jpeg':
                    case 'png':
                        break;
                    default:
                        continue;
                }

                this.dataTransfer.items.add(file);

                let reader = new FileReader();

                reader.onload = function (e: any) {
                    component.loadImages.push(e.target.result);
                }

                reader.readAsDataURL(file); // convert to base64 string
            }

            event.target.files = this.dataTransfer.files;
        }
    }

    removeImage(event: any) {
        this.dataTransfer.items.remove(event.target);
        event.target.remove();
    }

    public removeImagesList: string[] = [];

    remImage(event: any, image: any) {
        if (event.target.hasAttribute('remove')) {
            event.target.removeAttribute('remove');
            this.removeImagesList = this.removeImagesList.filter(x => x != image);
            event.target.style.opacity = '1';
        } else {
            event.target.setAttribute('remove', null);
            this.removeImagesList.push(image);
            event.target.style.opacity = '0.5';
        }
    }

    reset() {
        this.loadImages = [];
        this.dataTransfer.items.clear();
    }
}