resource "aws_default_vpc" "default_vpc" {
  tags = {
    Name = "default_vpc"
  }
}

resource "aws_security_group" "ec2_security_group" {
    name        = "ec2_security_group"
    ingress {
        from_port   = 8080
        to_port     = 8080
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    ingress {
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    ingress {
        from_port   = 443
        to_port     = 443
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
    tags = {
        Name           = "ec2_security_group"
        owner          = "keegan.oreilly@bbd.co.za"
        created-using  = "terraform"
    }
}

resource "tls_private_key" "ec2_key" {
    algorithm = "RSA"
    rsa_bits  = 4096
}

resource "aws_key_pair" "deployer" {
  key_name   = "deployer-key"
  public_key = tls_private_key.ec2_key.public_key_openssh
}

resource "aws_instance" "content-store-ec2-new" {
    ami           = "ami-0d940f23d527c3ab1"
    
    instance_type = "t2.micro"
    key_name      = aws_key_pair.deployer.key_name
    vpc_security_group_ids = [aws_security_group.ec2_security_group.id]
    tags = {
        Name           = "content-store-ec2"
        owner          = "keegan.oreilly@bbd.co.za"
        created-using  = "terraform"
    }
    connection {
        type        = "ssh"
        user        = "ubuntu"
        private_key = tls_private_key.ec2_key.private_key_pem
        host        = self.public_ip
    }
    provisioner "file" {
    source      = "../docker/docker-compose.yaml"
    destination = "/home/ubuntu/docker-compose.yaml"
    }
    provisioner "file" {
    source      = "../docker/mysql-persistence-config.sql"
    destination = "/home/ubuntu/mysql-persistence-config.sql"
    }
    provisioner "file" {
    source      = "../docker/repository-with-mysql-persistence.xml"
    destination = "/home/ubuntu/repository-with-mysql-persistence.xml"
    }
    provisioner "remote-exec" {
        inline = [
            "sudo apt update",
            "sudo apt install -y docker.io",
            "sudo apt install -y docker-compose",
            "sudo apt install -y git"
        ]
    }
}
    