package entity;

import java.time.LocalDate;

public class Fornecedor {
        private int id;
        private String nome;
        private String cnpj;
        private String email;
        private String telefone;
        private LocalDate dataContrato;
        private String endereco;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getCnpj() {
                return cnpj;
        }

        public void setCnpj(String cnpj) {
                this.cnpj = cnpj;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getTelefone() {
                return telefone;
        }

        public void setTelefone(String telefone) {
                this.telefone = telefone;
        }

		public LocalDate getDataContrato() {
			return dataContrato;
		}

		public void setDataContrato(LocalDate dataContrato) {
			this.dataContrato = dataContrato;
		}

		public String getEndereco() {
			return endereco;
		}

		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}
}
