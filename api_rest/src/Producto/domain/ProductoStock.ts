export class ProductoStock {
    value: number;

    constructor(value: number) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(typeof(this.value) !== 'number') {
            this.value = Number.parseInt(this.value);
        }

        if(this.value < 0) {
            throw new Error('Producto stock no puede ser menor a cero.');
        }
    
    }
}