data "aws_ami" "replace_ami_name" {
 most_recent = true


 filter {
   name   = "owner-alias"
   values = ["amazon"]
 }


 filter {
   name   = "name"
   values = ["amzn2-ami-hvm*"]
 }
}


resource "aws_instance" "replace_tf_name" {

 ami                         = "${data.aws_ami.replace_ami_id.id}"
 associate_public_ip_address = true
 instance_type               = "t2.micro"
 key_name                    = "darcy-key"
 vpc_security_group_ids      = ["sg-00297eccc99f4d349"]
 subnet_id                   = "subnet-07257a32c0ddfbc38"
 
 user_data = <<EOF
 #!/bin/bash
  yum update -y
  yum install -y httpd.x86_64
  systemctl start httpd.service
  systemctl enable httpd.service
  cat <<EOT >> /var/www/html/index.html
<html>
    <head>
    <meta charset="UTF-8">
    <title>Web App</title>
    </head>
    <h1><FONT COLOR=white>Pretend I'm a Beautiful Web App</h1>
    <BODY BGCOLOR="#c932d3">
    <h2><FONT COLOR=white>&#128525;Like, really beautiful&#128525;</h2>
    </body>
    </html>
EOT
  systemctl restart httpd.service

EOF

 tags = {
    Name = "replace_tag_name"
  }
}
