document.addEventListener('DOMContentLoaded', function() {
  // Dados dos produtos (simulando banco de dados)
  const products = [
    { id: 1, name: "Arroz 5kg", category: "mercearia", oldPrice: 22.90, newPrice: 18.90, discount: 17, image: "https://via.placeholder.com/200x150?text=Arroz" },
    { id: 2, name: "Feijão Carioca 1kg", category: "mercearia", oldPrice: 8.90, newPrice: 6.50, discount: 27, image: "https://via.placeholder.com/200x150?text=Feijão" },
    { id: 3, name: "Óleo de Soja 900ml", category: "mercearia", oldPrice: 7.49, newPrice: 5.99, discount: 20, image: "https://via.placeholder.com/200x150?text=Óleo" },
    { id: 4, name: "Tomate", category: "hortifruti", oldPrice: 6.99, newPrice: 4.99, discount: 29, image: "https://via.placeholder.com/200x150?text=Tomate" },
    { id: 5, name: "Alface Crespa", category: "hortifruti", oldPrice: 2.49, newPrice: 1.99, discount: 20, image: "https://via.placeholder.com/200x150?text=Alface" },
    { id: 6, name: "Filé de Frango", category: "carnes", oldPrice: 19.90, newPrice: 15.90, discount: 20, image: "https://via.placeholder.com/200x150?text=Frango" },
    { id: 7, name: "Cerveja Lata 350ml", category: "bebidas", oldPrice: 3.49, newPrice: 2.79, discount: 20, image: "https://via.placeholder.com/200x150?text=Cerveja" },
    { id: 8, name: "Sabão em Pó 1kg", category: "limpeza", oldPrice: 12.90, newPrice: 9.90, discount: 23, image: "https://via.placeholder.com/200x150?text=Sabão" }
  ];

  const promoGrid = document.getElementById('promo-grid');
  const filterButtons = document.querySelectorAll('.filter-btn');
  const carousel = document.querySelector('.carousel');
  const prevBtn = document.querySelector('.prev');
  const nextBtn = document.querySelector('.next');

  // Carrega todos os produtos
  function loadProducts(category = 'all') {
    promoGrid.innerHTML = '';
    
    const filteredProducts = category === 'all' 
      ? products 
      : products.filter(product => product.category === category);
    
    filteredProducts.forEach(product => {
      const productElement = document.createElement('div');
      productElement.className = 'promo-item';
      productElement.dataset.category = product.category;
      productElement.innerHTML = `
        <div class="promo-badge">-${product.discount}%</div>
        <img src="${product.image}" alt="${product.name}">
        <h3>${product.name}</h3>
        <p class="old-price">R$ ${product.oldPrice.toFixed(2).replace('.', ',')}</p>
        <p class="new-price">R$ ${product.newPrice.toFixed(2).replace('.', ',')}</p>
      `;
      promoGrid.appendChild(productElement);
    });
  }

  // Filtro por categoria
  filterButtons.forEach(button => {
    button.addEventListener('click', () => {
      filterButtons.forEach(btn => btn.classList.remove('active'));
      button.classList.add('active');
      loadProducts(button.dataset.category);
    });
  });

  // Controles do carrossel
  let scrollAmount = 0;
  const scrollStep = 270;
  const maxScroll = carousel.scrollWidth - carousel.clientWidth;

  prevBtn.addEventListener('click', () => {
    scrollAmount = Math.max(scrollAmount - scrollStep, 0);
    carousel.scrollTo({ left: scrollAmount, behavior: 'smooth' });
  });

  nextBtn.addEventListener('click', () => {
    scrollAmount = Math.min(scrollAmount + scrollStep, maxScroll);
    carousel.scrollTo({ left: scrollAmount, behavior: 'smooth' });
  });

  // Carrossel automático
  let autoScrollInterval = setInterval(() => {
    scrollAmount += scrollStep;
    if (scrollAmount >= maxScroll) scrollAmount = 0;
    carousel.scrollTo({ left: scrollAmount, behavior: 'smooth' });
  }, 3000);

  // Pausa o carrossel quando o mouse está sobre ele
  carousel.addEventListener('mouseenter', () => {
    clearInterval(autoScrollInterval);
  });

  carousel.addEventListener('mouseleave', () => {
    autoScrollInterval = setInterval(() => {
      scrollAmount += scrollStep;
      if (scrollAmount >= maxScroll) scrollAmount = 0;
      carousel.scrollTo({ left: scrollAmount, behavior: 'smooth' });
    }, 3000);
  });

  // Carrega os produtos inicialmente
  loadProducts();
});
