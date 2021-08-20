export class ImageLoader {

    public loadImage: any;
    public removedImage: string;
    public dataTransfer: DataTransfer = new DataTransfer();

    constructor() {}

    load(event: any) {

        let component = this;

        if (event.target.files && event.target.files[0]) {

            const file = event.target.files[0];

            let ext = file.name.match(/\.([^\.]+)$/)[1];

            switch (ext) {
                case 'jpg':
                case 'jpeg':
                case 'png':
                    break;
                default:
                    return;
            }

            this.dataTransfer.items.add(file);

            let reader = new FileReader();

            reader.onload = function (e: any) {
                component.loadImage = e.target.result;
            }

            reader.readAsDataURL(file); // convert to base64 string

            event.target.files = this.dataTransfer.files;
        }
    }

    removeImage(event: any) {
        this.dataTransfer.items.remove(event.target);
        this.loadImage = null;
        event.target.remove();
    }

    reset() {
        this.loadImage = null;
        this.dataTransfer.items.clear();
    }
}