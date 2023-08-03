import boto3
import json

def lambda_handler(event, context):
    security_group_id = "sg-056c267ca02beb53d"
    ports = [5671, 5672, 15671, 15672]
    protocol = "tcp"
    cidr = event['cidr'] #"10.10.64.0/24"
    description = "SG for RMQ"
    ec2 = boto3.resource('ec2')
    security_group = ec2.SecurityGroup(security_group_id)

    #if cidr != "":
    for p in ports:
        port_range_start = p
        port_range_end = p
        
        security_group.authorize_ingress(
                DryRun=False,
                IpPermissions=[
                    {
                        'FromPort': port_range_start,
                        'ToPort': port_range_end,
                        'IpProtocol': protocol,
                        'IpRanges': [
                            {
                                'CidrIp': cidr,
                                'Description': description
                            },
                        ]
                    }
                ]
            )
    return {
        'Result': 'Ok'
    }