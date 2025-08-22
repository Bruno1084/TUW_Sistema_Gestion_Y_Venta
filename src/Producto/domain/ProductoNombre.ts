export class ProductoNombre {
    value: string;

    constructor(value: string) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value.length > 50) {
            throw new Error('Producto nombre no puede superar los 50 caracteres');
        }
    }
}