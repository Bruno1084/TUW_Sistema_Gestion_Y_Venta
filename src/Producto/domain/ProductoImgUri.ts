export class ProductoImgUri {
    value: string;

    constructor(value: string) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        // Value Object rules not defined yet
    }
}