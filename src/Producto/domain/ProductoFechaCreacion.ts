export class ProductoFechaCreacion {
    value: Date;

    constructor(value: Date) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value.getTime() > new Date().getTime()) {
            throw new Error('Producto fecha creación no puede ser mayor a la fecha actual.');
        }
    }
}