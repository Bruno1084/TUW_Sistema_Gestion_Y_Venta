export class ProductoCodigoBarra {
    value: string;

    constructor(value: string) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value.length > 80) {
            throw new Error('Producto codigo barra no puede superar los 80 caracteres');
        }
    }
}