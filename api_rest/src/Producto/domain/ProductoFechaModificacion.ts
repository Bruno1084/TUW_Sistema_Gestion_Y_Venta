export class ProductoFechaModificacion {
    value: Date;

    constructor(value: Date) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value.getTime() > new Date().getTime()) {
            throw new Error('Producto fechaModificación no puede ser mayor a la fecha actual.');
        }
    }
}