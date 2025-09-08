import crypto from "crypto";

export class CajeroContrasenia {
    readonly value: { hash: string; salt: string };

    constructor(contraseniaPlano: string, salt?: string, hash?: string) {
        const saltFinal = salt ?? crypto.randomBytes(16).toString("hex");
        const hashFinal =
            hash ??
            crypto.pbkdf2Sync(contraseniaPlano, saltFinal, 10000, 64, "sha512").toString("hex");

        this.value = { hash: hashFinal, salt: saltFinal };

        this.checkValue();
    }

    private checkValue() {
        if (!this.value.hash || !this.value.salt) {
            throw new Error("La contraseña no puede estar vacía");
        }
    }

    comparar(contraseniaPlano: string): boolean {
        const hashVerificar = crypto
            .pbkdf2Sync(contraseniaPlano, this.value.salt, 10000, 64, "sha512")
            .toString("hex");

        return this.value.hash === hashVerificar;
    }

    static fromHashed(hash: string, salt: string): CajeroContrasenia {
        return new CajeroContrasenia("", salt, hash);
    }
}
