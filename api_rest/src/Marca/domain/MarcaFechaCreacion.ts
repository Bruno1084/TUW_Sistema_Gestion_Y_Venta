export class MarcaFechaCreacion {
    value: Date;

    constructor(value: Date) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value.getTime() > new Date().getTime()) {
            throw new Error('Marca fecha creación no puede ser mayor a la fecha actual.');
        }
    }
}