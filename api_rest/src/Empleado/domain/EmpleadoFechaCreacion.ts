export class EmpleadoFechaCreacion {
    value: Date;

    constructor(value: Date) {
        this.value = value;
        this.checkValue();
    }

    private checkValue() {
        // Error al generar empleados.
        // if(this.value.getTime() > new Date().getTime()) {
        //     throw new Error('FechaCreación Empleado no puede ser mayor a la fecha actual.');
        // }
    }
}