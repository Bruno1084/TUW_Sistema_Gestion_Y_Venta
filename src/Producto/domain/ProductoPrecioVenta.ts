export class ProductoPrecioVenta {
    value: number;

    constructor(value: number) {
        this.value = value;
        
        this.checkValue();
    }

    private checkValue() {
        if(this.value <= 0) {
            throw new Error('Producto precio venta no puede ser menor a cero.')
        }
    }
}