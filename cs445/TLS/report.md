# 1. Overview of Transport Layer Security

## 1.1. Introduction to Transport Layer Security

Transport Layer Security (TLS) is a critical protocol that ensures the privacy and security of
data transmitted between two communicating applications. It operates by encrypting all packets
exchanged between a client and server, rendering them unreadable to external parties. This
encryption safeguards the integrity and confidentiality of the data, making TLS indispensable for
modern online interactions.

TLS is typically added between the application and transport layers, without affecting the
underlying protocols. This is possible because these layers operate independently and do not
require knowledge of the payload's contents. As a result, TLS is often implemented as an extension
of the preceding protocol, indicated by an "S" at the end of the name. Some popular examples
include "HTTPS" for HTTP, "SMTPS" for SMTP, and "FTPS" for FTP. These secure versions ensure that
data transmission over the internet remains protected from potential threats.

## 1.2. Introduction to Cryptography Basics

Cryptography, while an intricate and expansive topic, is essential to understanding the
foundational concepts of how TLS secures data transmission. Although the mathematics behind it is
not fundamentally necessary in this aspect, it is crucial to be aware of the two main types of
cryptography, their strengths and weaknesses, and the notable features they provide.

### 1.2.1. Symmetric Encryption

Symmetric encryption is one of the main types of encryption and is likely the one that comes to
mind when discussing cryptography. This method uses a single secret key to encrypt and decrypt
data. Its simple nature allows computations to be quick and efficient, making it the desired
scheme for bulk data. However, challenges arise when there is a need to share this encrypted data,
as it would necessitate sharing the secret key. If the key is compromised during this process, it
would jeopardize the security of all encrypted data.

### 1.2.2. Asymmetric Encryption

Asymmetric encryption, also known as Public Key Cryptography (PKC), is another type of encryption
and is considerably more complex. This system employs the use of two keys: a public key, which can
be revealed to anyone, and a private key, which must be kept secret. Their mathematical
relationship allows for encryption done by the public key to only be decrypted by the private key,
and vice versa. This feature facilitates easy data encryption between two parties, but also allows
for a method of authentication known as a digital signature. However, challenges arise when there
is a need to encrypt a large amount of data, as asymmetric encryption is slow and computationally
expensive.

### 1.2.3. Diffie-Hellman Key Exchange Algorithm

A key exchange algorithm is a method that allows two parties to generate a shared secret,
leveraging the features of PKC to keep sensitive cryptographic information secure. These
algorithms ensure that even if communication for this key derivation is compromised, the
interceptor does not have enough information to piece together the shared secret themselves.

Among the various types of key exchange algorithms, the Diffie-Hellman Key Exchange Algorithm (DH)
is the most widely used and favored among many protocols, including TLS. This algorithm can be
metaphorically compared to mixing paint, where it’s easy to mix two colors together, but extremely
challenging to separate them back into their original colors. In the context of DH, both parties
start with a known public value. They then combine this public value with their uniquely generated
private key to create a public key. This public key is then exchanged with the other party, who
combines it with their private key to create a shared secret.

# 2. Design of Transport Layer Security

## 2.1. Establishing a Reliable Connection

The process of incorporating TLS into the layered stack starts with the creation of a reliable
connection between the client and server. As TLS is not a standalone layer, it is dependent on the
assistant of other layers for its effective operation. Mainly, the Transmission Control Protocol
(TCP) is crucial as it provides resistance to transport errors, lost packets, and possible
interruptions during transmission. Its robustness and reliability make it an ideal choice for
ensuring the smooth function of TLS.

## 2.2. Transport Layer Security Handshake

The TLS Handshake is a critical component of the TLS protocol, serving as the initial phase in
establishing a secure connection between two parties. This process is vital as it facilitates the
authentication of the communicating parties and the establishment of encryption keys before the
transmission of the actual data.

### 2.2.1. Client Hello

The TLS Handshake starts with a Client Hello message, which contains various fields to help set
up the process to negotiate on cryptographic parameters. While there are certain optional fields
and extensions that can be added, the most important include the supported TLS versions, supported
cipher suites, and client-generated random data.

### 2.2.3. Server Hello

Upon receiving the Client Hello message, the server reciprocates with a Server Hello message. This
response encapsulates the selected values essential for the creation of cryptographic parameters.
Similar in design, while there are certain optional fields and extensions that can be added, the
most important include the chosen TLS versions, chosen cipher suite, and server-generated random
data.

### 2.2.3. Server Certificate

Before continuing, the server is required to authenticate themselves by sending a Server
Certificate message containing a chain of SSL certificates. This allows the client to verify all
communication going forward is being made by the server and has not been tampered with by an
outside party. In most cases, the client does not have to reciprocate a Client Certificate
message, though it can be requested by the server.

### 2.2.4. Server Key Exchange

Once the cryptographic parameters and authentication have been established, the key exchange can
begin. First, the server defines a public value and combines it with their unique private key to
form a public key. These two values are then sent to the client. For an extra layer of
verification, the server typically signs these values.

### 2.2.5. Server Hello Done

Finally, to mark the end of its part of the key exchange and first round-trip, the server sends a
Server Hello Done message to the client and waits for a response. There are no significant
contents to this message.

### 2.2.6. Client Key Exchange

Upon receiving the Server Key Exchange message, the client combines the provided public value with
their unique private key to derive a public key. This public key is then sent across the
transmission stream to the server.

At this point, both parties should have all the required cryptographic information to compute a
shared pre-master secret by combining their private key with the other party’s public key. The
final step to deriving the master secret is combing the pre-master secret with the two random
values generated in the hello message exchange and passing it through a fixed-length pseudorandom
hash function.

### 2.2.7. Client Change Cipher Spec

After generating the master secret, the client informs the server that all subsequent
communication will be carried out using the agreed upon encryption scheme through a Change Cipher
Spec message.

### 2.2.8. Client Finished

Immediately after sending the Client Change Cipher Spec message, the client sends a Client
Finished message to verify the key exchange, authentication, and other cryptographic operations
were successful. This message contains a hash of all previous handshake messages along with a
"client finished" string and is encrypted before being sent.

### 2.2.9. Server Change Cipher Spec

Once receiving the Client Finished, the server reciprocates an identical Change Cipher Spec
message to notify the client all subsequent communication will be carried out using the agreed
upon encryption scheme.

### 2.2.10. Server Finished

Identical to the Client Finished, the server also sends a Server Finished message to ensure the
TLS Handshake was successful and not tampered with.

## 2.3. Transport Layer Security Record

The TLS Record is the second part of the TLS protocol that handles the encryption and decryption
of data using the derived master secret from the TLS Handshake. In addition, the TLS Record also
handles the fragmentation and reassembly of blocks of data. This is done to ensure efficient and
reliable data transmission over the network. Furthermore, the protocol also calculates and
verifies a message authentication code for each data block. This is used to verify the integrity
and authenticity of the received data, ensuring it has not been tampered with during transmission.

# 3. Quantum-Resistant Transport Layer Security

## 3.1. Introduction

The advent of quantum computers poses a significant risk to the security of current encryption
methods. These methods are based on the computational difficulty of solving complex mathematical
problems, a task that even classical supercomputers find challenging. Quantum computers, however,
can efficiently solve these problems, thereby weakening or even rendering current encryption
techniques useless. While symmetric encryption remains relatively secure against these attacks,
asymmetric encryption is far more vulnerable, with certain quantum-focused techniques threatening
to break the encryption entirely.

In response to the growing threat, recent development has proposed solutions such as Post-Quantum
Cryptography (PQC) and Quantum Key Distribution (QKD). PQC operates on the existing framework of
difficult mathematical problems, while QKD uses the principles of physics to create a secure
environment resistant to even the most exhaustive attacks. However, there are concerns about the
future viability of PQC and the lack of rigorous testing for QDK. To address these issues, Hybrid
TLS has been proposed, which combines traditional and post-quantum encryption to ensure security
even if one type is compromised.

## 3.2. Background Information

### 3.2.1. Quantum Key Distribution

Quantum Key Distribution (QKD) is a cryptographic technique that aims to securely generate a
secret key by transmitting quantum signals between authenticated partners. The security of QKD
relies on two theorems of quantum physics: the no-cloning theorem and Heisenberg’s uncertainty
principle. These principles state that it’s impossible to obtain an identical copy of an arbitrary
unknown quantum state, and that the measurement of a quantum signal causes a perturbation in its
state that cannot be recovered. Therefore, any attempt to eavesdrop on the quantum channel will
inevitably alter the state of the quantum signals exchanged, alerting the involved parties of the
presence of an eavesdropper. After the transmission of the quantum signals, both the sender and
receiver can execute a series of post-processing operations to estimate how much information about
the quantum signal has been leaked to an outside party. If these operations reveal the presence of
an eavesdropper on the quantum channel, the protocol is aborted. Otherwise, a key can be
distilled, the security of which is almost universally guaranteed by the laws of quantum physics.
 
### 3.2.2 Post-Quantum Cryptography

Post-Quantum Cryptography (PQC) is a set of cryptographic algorithms designed to be secure against
quantum computers. Unlike Quantum Key Distribution (QKD), PQC operates on the same principles as
classical asymmetric cryptography, including key-pair generation, digital signatures, and key
encapsulation/decapsulation. While classical cryptography is insecure against quantum computers,
PQC secures its algorithms using mathematical operations like lattice-based or hash-based
cryptography, against which quantum algorithms currently have little to no advantage. Although PQC
algorithms are not theoretically secure against all quantum attacks, they are considered to be
much more secure than currently used algorithms.

## 3.3. Results and Discussion

The research introduces a novel scheme for integrating PQC and QKD into TLS, enhancing its
security against quantum computing threats while maintaining high performance in session key
generation. This quantum-resistant TLS architecture can combine QKD-based keys on both client and
server sides with any other classical or PQC cryptographic algorithm of choice. This paves the way
towards achieving maximum security against known and future unknown attacks. While the adoption of
the proposed quantum-resistant TLS protocol comes at a considerable cost of communication, it
significantly enhances the security of communications. Communications are now protected by two
different quantum-resistant cryptographic assumptions, making it a promising solution for secure
data transmission in the era of quantum computing. However, for this experimental solution to be
widely used in real communication networks, several open research questions and challenges require
further investigation.

The analysis of PQC cipher suites and traditional cipher suites reveals that only one traditional
cipher suite surpassed a PQC cipher suite in terms of time. However, this traditional cipher suite
has been deemed insecure and is not used in practice. Conversely, the post-quantum solution,
especially its lighter versions, shows promising results in terms of speed for generating client
and server key exchanges. The integration of QKD with PQC introduces an overhead, though this
overhead is justified as it ensures the security of the resultant master secret by two different
quantum-resistant cryptographic assumptions.

# 4. Conclusion

Transport Layer Security (TLS) is a crucial protocol that ensures the confidentiality and
integrity of data exchanged between web servers and clients. It plays a vital role in protecting
sensitive information such as financial data and personal details, making it a fundamental part
of modern online communication. Without TLS, online interactions would be vulnerable to various
security threats, including eavesdropping and data tampering.

However, the emergence of quantum computing presents a significant challenge to traditional
cryptography, including TLS. Certain algorithms can break the mathematical foundations of current
encryption schemes, making them vulnerable. In response to this threat, researchers are developing
post-quantum cryptography algorithms and Quantum Key Distribution that are resistant to quantum
computing attacks. These algorithms are more resilient to quantum-based attacks, but transitioning
to them poses another challenge. The integration of PQC algorithms into TLS is an ongoing effort,
with standardization bodies working to define standards for quantum-resistant TLS protocols. As
quantum computing technology continues to evolve, transitioning to quantum-resistant TLS will be
essential to maintaining the security of our online communications.
