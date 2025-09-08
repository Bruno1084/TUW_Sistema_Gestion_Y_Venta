export class ProductoDescripcion {
    value: string;

    constructor(value: string) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value.length > 255) {
            throw new Error('Producto descripción no puede superar los 255 caracteres.');
        }
    }
}