export class ProveedorFechaModificacion {
    value: Date;

    constructor(value: Date) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value.getTime() > new Date().getTime()) {
            throw new Error('Proveedor fecha modificacion no puede ser mayor a la fecha actual.');
        }
    }
}