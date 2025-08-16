export class EmpleadoDireccion {
    value: string;

    constructor(value: string) {
        this.value = value;

        this.checkValue();
    }

    private checkValue() {
        if(this.value.length < 3) {
            throw new Error('Empleado dirección no puede ser menor a 3 caracteres');
        }
    }

}