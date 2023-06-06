    class Pessoa {
        private String nome;
        private String nif;
        private String morada;
        private String contactoTelefonico;

        public Pessoa(String nome, String nif, String morada, String contactoTelefonico) {
            this.nome = nome;
            this.nif = nif;
            this.morada = morada;
            this.contactoTelefonico = contactoTelefonico;
        }

        // getters e setters

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getNif() {
            return nif;
        }

        public void setNif(String nif) {
            this.nif = nif;
        }

        public String getMorada() {
            return morada;
        }

        public void setMorada(String morada) {
            this.morada = morada;
        }

        public String getContactoTelefonico() {
            return contactoTelefonico;
        }

        public void setContactoTelefonico(String contactoTelefonico) {
            this.contactoTelefonico = contactoTelefonico;
        }

    }
