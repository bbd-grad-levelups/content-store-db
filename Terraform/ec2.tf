resource "aws_security_group" "ec2_security_group" {
    vpc_id = module.vpc.vpc_id
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

resource "aws_instance" "content-store-ec2" {
    ami           = "ami-0d940f23d527c3ab1"
    instance_type = "t2.micro"
    key_name      = aws_key_pair.deployer.key_name
    subnet_id              = module.vpc.public_subnets[0] 
    vpc_security_group_ids = [aws_security_group.ec2_security_group.id]
    tags = {
        Name           = "content-store-ec2"
        owner          = "keegan.oreilly@bbd.co.za"
        created-using  = "terraform"
    }
    # connection {
    #     type        = "ssh"
    #     user        = "ubuntu"
    #     private_key = tls_private_key.ec2_key.private_key_pem
    #     host        = self.public_ip
    # }
    # provisioner "remote-exec" {
    #     inline = [
    #         "sudo apt update",
    #         "sudo apt install -y docker.io"
    #     ]
    # }
    # depends_on = [aws_key_pair.deployer, aws_security_group.ec2_security_group]
}
    