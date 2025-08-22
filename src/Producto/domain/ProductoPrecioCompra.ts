export class ProductoPrecioCompra {
    value: number;

    constructor(value: number) {
        this.value = value;
        
        this.checkValue();
    }

    private checkValue() {
        if(this.value <= 0) {
            throw new Error('Producto precio compra no puede ser menor a cero.')
        }
    }
}